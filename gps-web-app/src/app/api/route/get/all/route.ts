import { NextRequest, NextResponse } from "next/server";
import { Pagination } from "@/lib/definitions/utils/pagination";

export async function GET(req: NextRequest) {
  const { searchParams } = new URL(req.url);
  const pageNumber = Number(searchParams.get("page") || 0);
  const pageSize = Number(searchParams.get("size") || 20);

  const pagination: Pagination = { pageNumber, pageSize };

  const response = await fetch(`${process.env.API_URL}/route/get/all?page=${pageNumber}&size=${pageSize}`, {
    method: "GET",
    headers: { "Content-Type": "application/json" },
  });

  if (!response.ok) {
    console.error("Error fetching routes:", response.statusText);
    return new Response("Failed to fetch routes", { status: 500 });
  }

  const data = await response.json();
  console.log("Fetched routes data (NEXT_API):", data);

  return NextResponse.json({
    pagination,
    routes: data.content || [],
  });
}