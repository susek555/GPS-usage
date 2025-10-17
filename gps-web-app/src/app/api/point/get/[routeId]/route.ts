import { NextRequest, NextResponse } from "next/server";
import { Pagination } from "@/lib/definitions/utils/pagination";

export async function GET(
    req: NextRequest,
    { params }: { params: { routeId: string } }
) {
  const { searchParams } = new URL(req.url);
  const pageNumber = Number(searchParams.get("pageNumber") || 0);
  const pageSize = Number(searchParams.get("pageSize") || 100);
  const { routeId } = await params;

  const pagination: Pagination = { pageNumber, pageSize };

  const response = await fetch(`${process.env.API_URL}/point/get/${routeId}?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
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
    points: data.content || [],
  });
}