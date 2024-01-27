import React, { useEffect, useRef } from 'react';
import maplibregl from 'maplibre-gl';
import 'maplibre-gl/dist/maplibre-gl.js';

const Map = () => {
  const map = useRef<any>(undefined);
  const mapRef = useRef<any>(undefined);

  const customMarkerHtml = `<div>
  <span class="icon icon-pin" style="height:40px; width: 40px; transform: translate(0%, -50%);"></span>
</div>`;

  useEffect(() => {
    if (!mapRef.current) {
      return;
    }

    map.current = new maplibregl.Map({
      container: mapRef.current,
      style: 'https://api.maptiler.com/maps/streets-v2/style.json?key=3EaejCgBdBIYb7EnNVDU',
      center: [9.597767047178365, 51.590195755881155],
      pitch: 50,
      zoom: 16,
    });

    if (map.current) {
      new maplibregl.Marker({
        element: document.createRange().createContextualFragment(customMarkerHtml)
          .firstChild as HTMLElement,
      })
        .setLngLat([9.59557968198547, 51.58965393386646])
        .addTo(map.current);
    }

    // map.current.addControl(new maplibregl.NavigationControl());
    // map.current.addControl(new maplibregl.FullscreenControl());
    map.current.on('click', getMousePosition.bind(this));

    return () => {
      map.current.off('click', getMousePosition.bind(this));
      map.current.remove();
    };
  }, []);

  // this function sets a marker where the user clicked
  const setMarker = (e: any) => {
    console.log(e.lngLat.lng, e.lngLat.lat);
    const marker = new maplibregl.Marker({
      element: document.createRange().createContextualFragment(customMarkerHtml)
        .firstChild as HTMLElement,
    })
      .setLngLat([e.lngLat.lng, e.lngLat.lat])
      .addTo(map.current);

    console.log(marker);
  };

  // function to get coordiantes of the mouse cursor
  const getMousePosition = (e: any) => {
    const coordinates = e.lngLat;
    setMarker(e);
    console.log(coordinates);
  };

  return (
    <div
      id="map"
      className="map"
      ref={mapRef}
      style={{ height: '460px', width: '100%', borderRadius: '25px', overflow: 'hidden' }}
    ></div>
  );
};
export default Map;
