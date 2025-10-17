import { Point } from "@/lib/definitions/data/point";
import { Route } from "@/lib/definitions/data/route";
import { Pagination } from "@/lib/definitions/utils/pagination";

export async function fetchAllRoutes(pagination: Pagination) : Promise<{pagination:  Pagination, routes: Route[]}> {
    const params = new URLSearchParams({
    pageNumber: pagination.pageNumber.toString(),
    pageSize: pagination.pageSize.toString(),
  });

  const response = await fetch(`/api/route/get/all?${params.toString()}`);

  if (!response.ok) {
    throw new Error("Failed to fetch routes");
  }

  console.log("Fetch all routes response (FETCHER):", response);

  return response.json();
}

export async function fetchRoute(id: number) : Promise<Route> {
  const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/route/get/${id}`);

  if (!response.ok) {
    throw new Error("Failed to fetch route");
  }

  console.log("Fetched route (FETCHER):", response )

  return response.json()
}

export async function fetchRoutePoints(routeId: number) : Promise<Point[]> {
  let points: Point[] = [];
  let pagination: Pagination = { pageNumber: 0, pageSize: 100 };
  let hasMoreData = true;

  do {
    const params = new URLSearchParams({
      pageNumber: pagination.pageNumber.toString(),
      pageSize: pagination.pageSize.toString(),
    });

    const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/api/point/get/${routeId}?${params.toString()}`);

    if (!response.ok) {
      throw new Error("Failed to fetch route points");
    }

    const data = await response.json();
    points = points.concat(data.points);
    pagination = data.pagination;
    pagination.pageNumber += 1;
    hasMoreData = data.points.length != 0;
    console.log(data.points.length);
  } while (hasMoreData);

  console.log("Fetch route points response (FETCHER):", points);

  return points;
}
