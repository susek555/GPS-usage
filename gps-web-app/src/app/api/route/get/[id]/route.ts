import { NextRequest, NextResponse } from "next/server";

export async function GET(
    req: NextRequest,
    { params } : { params: { id: string } }
) {
    const { id } = await params;

    const response = await fetch(`${process.env.API_URL}/route/${id}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
    });

    if (!response.ok) {
        console.error("Error fetching route:", response.statusText);
        return new Response("Failed to fetch route", { status: 500 });
    }

    const data = await response.json();
    console.log("Fetched route data (NEXT_API):", data);

    return NextResponse.json(data);
}