import { Route } from "@/lib/definitions/data/route";
import { Pagination } from "@/lib/definitions/utils/pagination";

export async function fetchAllRoutes(pagination: Pagination) : Promise<{pagination:  Pagination, routes: Route[]}> {
    const params = new URLSearchParams({
    pageNumber: pagination.pageNumber.toString(),
    pageSize: pagination.pageSize.toString(),
  });

  const response = await fetch(`${process.env.NEXT_PUBLIC_URL}/api/route/get/all?${params.toString()}`);

  if (!response.ok) {
    throw new Error("Failed to fetch routes");
  }

  return response.json();
}
