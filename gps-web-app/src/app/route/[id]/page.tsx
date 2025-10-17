import { RouteInfo } from "./RouteInfo"
import { Route } from "@/lib/definitions/data/route"
import { fetchRoute } from "@/lib/data/route/fetchers";

export default async function RouteView(props: {params: Promise<{id: string }>}) {
    const params = props.params
    const id = (await params).id

    const route: Route = await fetchRoute(parseInt(id))

    return(
        <RouteInfo route={route}/>
    )
}