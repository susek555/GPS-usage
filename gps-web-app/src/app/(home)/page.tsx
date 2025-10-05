"use client";

import { useEffect, useState } from "react";
import { fetchAllRoutes } from "@/lib/data/route/fetchers";
import { Route } from "@/lib/definitions/data/route";

export default function RouteList() {
  const [routes, setRoutes] = useState<Route[]>([]);
  const [pageNumber, setPageNumber] = useState(0);

  useEffect(() => {
    fetchAllRoutes({ pageNumber, pageSize: 20 }).then(data => setRoutes(data.routes));
  }, [pageNumber]);

  return (
    <div>
      <h2>Routes</h2>
      <ul>
        {routes.map(route => (
          <li key={route.id}>{route.name}</li>
        ))}
      </ul>
      <button onClick={() => setPageNumber(p => p + 1)}>Next page</button>
    </div>
  );
}