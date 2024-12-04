    package com.example.mobilecatsformaps.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Auto-generated ID
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: String, // This can be a comma-separated string or use a separate table for tags
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "contact_info") val contactInfo: String,
    @ColumnInfo(name = "approval_status") val approvalStatus: Boolean,
    @ColumnInfo(name = "social_worker_notes") val socialWorkerNotes: String? = null,
    @ColumnInfo(name = "related_services_links") val relatedServicesLinks: String? = null,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "target_focus_population") val targetFocusPopulation: String?,
    @ColumnInfo(name = "schedule_recurrence") val scheduleRecurrence: String?,
    @ColumnInfo(name = "registration_info") val registrationInfo: String?,
    @ColumnInfo(name = "accessibility_transportation") val accessibilityTransportation: String?,
    @ColumnInfo(name = "volunteering_opportunities") val volunteeringOpportunities: String?
)