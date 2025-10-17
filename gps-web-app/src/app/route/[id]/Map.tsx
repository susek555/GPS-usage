"use client";

import { Point } from "@/lib/definitions/data/point";
import { MapContainer, TileLayer, CircleMarker, Polyline, useMap } from "react-leaflet";
import "leaflet/dist/leaflet.css";



interface MapProps { points: Point[] }

function FitBounds({ points }: MapProps) {
  const map = useMap();
  if (points.length > 0) {
    const bounds = points.map(p => [p.latitude, p.longitude] as [number, number]);
    map.fitBounds(bounds, { padding: [50, 50] });
  }
  return null;
}

export default function Map({ points }: MapProps) {
  if (!points.length) return <p>Brak punktów</p>;

  const path = points.map(p => [p.latitude, p.longitude] as [number, number]);

  return (
    <MapContainer style={{ height: "600px", width: "100%" }}>
      <TileLayer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        // attribution="&copy; OpenStreetMap contributors"
      />
      <Polyline positions={path}/>
      {points.map((p, i) => (
        <CircleMarker
          key={i}
          center={[p.latitude, p.longitude]}
          pathOptions={{
            radius: 2, // rozmiar kropki
            fillColor: "red", // kolor wypełnienia
            color: "darkred", // kolor obramówki
            weight: 1, // grubość obramówki
            fillOpacity: 0.8, // przezroczystość wypełnienia
          }}
        />
      ))}
      <FitBounds points={points} />
    </MapContainer>
  );
}


// import { GoogleMap, LoadScript, Polyline, Marker } from "@react-google-maps/api";

// const containerStyle = {
//   width: "100%",
//   height: "600px",
// };

// export default function Map({ points }: {points: Point[]}) {
//   if (!points || points.length === 0) return <p>Brak punktów trasy</p>;

//   const center = {
//     lat: points[0].latitude,
//     lng: points[0].longitude,
//   };

//   const path = points.map(p => ({ lat: p.latitude, lng: p.longitude }));

//   return (
//     <LoadScript googleMapsApiKey={process.env.NEXT_PUBLIC_GOOGLE_MAPS_API_KEY!}>
//       <GoogleMap mapContainerStyle={containerStyle} center={center} zoom={13}>
//         {/* Linia trasy */}
//         <Polyline
//           path={path}
//           options={{
//             strokeColor: "#FF0000",
//             strokeOpacity: 0.8,
//             strokeWeight: 3,
//           }}
//         />

//         {/* Punkty trasy */}
//         {points.map((p, i) => (
//           <Marker
//             key={i}
//             position={{ lat: p.latitude, lng: p.longitude }}
//             label={(i + 1).toString()}
//           />
//         ))}
//       </GoogleMap>
//     </LoadScript>
//   );
// }
