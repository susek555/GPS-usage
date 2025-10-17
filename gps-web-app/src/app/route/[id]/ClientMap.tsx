"use client";

import { Point } from "@/lib/definitions/data/point";
import dynamic from "next/dynamic";

// Dynamic import z wyłączonym SSR
const Map = dynamic(() => import("./Map"), {
  ssr: false,
  loading: () => <div className="h-[600px] w-full bg-gray-100 flex items-center justify-center">Ładowanie mapy...</div>
});

interface ClientMapProps {
  points: Point[];
}

export default function ClientMap({ points }: ClientMapProps) {
  return <Map points={points} />;
}