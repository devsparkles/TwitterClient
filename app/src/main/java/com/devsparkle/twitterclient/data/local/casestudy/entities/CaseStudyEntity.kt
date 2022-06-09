package com.devsparkle.twitterclient.data.local.casestudy.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Entity for the case study
 * @param teaser       text under the image
 * @param coverImgUrl  link for the image of the cover for each case study
 */
@Entity(tableName = "casestudies")
data class CaseStudyEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "teaser") var teaser: String = "",
    @ColumnInfo(name = "coverimageUrl") var coverimageUrl: String = "",
)

