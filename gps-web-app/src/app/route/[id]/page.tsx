import { RouteInfo } from "./RouteInfo"
import { Route } from "@/lib/definitions/data/route"
import { Point } from "@/lib/definitions/data/point";
import { fetchRoute, fetchRoutePoints } from "@/lib/data/route/fetchers";
import ClientMap from "./ClientMap"; // ← Używamy wrappera

export default async function RouteView({ params }: {params: Promise<{id: string }>}) {
    const id = (await params).id

    const route: Route = await fetchRoute(parseInt(id))
    const points: Point[] = await fetchRoutePoints(parseInt(id))

    return(
        <>
            <RouteInfo route={route}/>
            <div className="mt-4"/>
            <ClientMap points={points} />
                {/* <h3 className="text-lg font-semibold mb-2">Route Points</h3>
                {points.length > 0 ? (
                    <ul className="space-y-2">
                        {points.map(point => (
                            <li key={point.latitude} className="bg-black-50 p-3 rounded-md shadow-sm border border-gray-200">
                                <span className="font-medium">{point.latitude}, {point.longitude}</span>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p className="text-gray-500">No points available for this route</p>
                )}
            </div> */}
        </>
    )
}