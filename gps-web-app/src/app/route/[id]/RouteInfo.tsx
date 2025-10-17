import { Route } from "@/lib/definitions/data/route";


export async function RouteInfo({ route }: {route: Route}) {
    return (
        <>
            <p>Route Info</p>
            <p>Name: {route.name}</p>
            <p>Number of points: {route.numberOfPoints}</p>
            <p>Start time: {route.time}</p>
        </>
    );
}