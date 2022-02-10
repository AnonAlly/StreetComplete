package de.westnordost.streetcomplete.data.osm.geometry

object RelationGeometryTable {
    const val NAME = "elements_geometry_relations"

    object Columns {
        const val ID = "id"
        const val GEOMETRY_POLYGONS = "geometry_polygons"
        const val GEOMETRY_POLYLINES = "geometry_polylines"
        const val CENTER_LATITUDE = "latitude"
        const val CENTER_LONGITUDE = "longitude"
        const val MIN_LATITUDE = "min_lat"
        const val MIN_LONGITUDE = "min_lon"
        const val MAX_LATITUDE = "max_lat"
        const val MAX_LONGITUDE = "max_lon"
    }

    const val CREATE = """
        CREATE TABLE $NAME (
            ${Columns.ID} int PRIMARY KEY,
            ${Columns.GEOMETRY_POLYLINES} blob,
            ${Columns.GEOMETRY_POLYGONS} blob,
            ${Columns.CENTER_LATITUDE} double NOT NULL,
            ${Columns.CENTER_LONGITUDE} double NOT NULL,
            ${Columns.MIN_LATITUDE} double NOT NULL,
            ${Columns.MAX_LATITUDE} double NOT NULL,
            ${Columns.MIN_LONGITUDE} double NOT NULL,
            ${Columns.MAX_LONGITUDE} double NOT NULL
        );
    """

    const val SPATIAL_INDEX_CREATE = """
        CREATE INDEX elements_geometry_bounds_index_relations ON $NAME(
            ${Columns.MIN_LATITUDE},
            ${Columns.MAX_LATITUDE},
            ${Columns.MIN_LONGITUDE},
            ${Columns.MAX_LONGITUDE}
        );
    """

}
