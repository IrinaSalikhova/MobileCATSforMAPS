package com.example.mobilecatsformaps.database

class SampleAssets {
    fun getSampleAssets(): List<Asset> {
        return listOf(
            asset1,
            asset2,
            asset3,
            asset4,
            asset5
        )
    }

    val asset1 = Asset(
        name = "Ottawa Community Health Center",
        category = "Health, Social, Support",
        latitude = 45.4215,
        longitude = -75.6972,
        address = "123 Main Street, Ottawa, ON",
        contactInfo = "(613) 555-0199",
        approvalStatus = true,
        socialWorkerNotes = "Confidential, accessible for registered social workers only.",
        relatedServicesLinks = "Nearby services include community therapy centers and wellness workshops.",
        description = "A place offering health and wellness services, including preventive care, physical therapy, and mental health support.",
        targetFocusPopulation = "Elderly, low-income families, individuals with chronic health conditions",
        scheduleRecurrence = "Mon-Fri, 9 AM - 5 PM",
        registrationInfo = "Free, appointment required",
        accessibilityTransportation = "Wheelchair accessible; nearby public transit",
        volunteeringOpportunities = "Yes, please call"
    )

    val asset2 = Asset(
        name = "Ottawa Food Bank",
        category = "Food, Support, Community",
        latitude = 45.4215,
        longitude = -75.6981,
        address = "860 Industrial Avenue, Ottawa, ON",
        contactInfo = "(613) 123-4567",
        approvalStatus = true,
        socialWorkerNotes = "Accessible for families in need, referrals preferred.",
        relatedServicesLinks = "Nearby community kitchens and food assistance programs.",
        description = "Provides food and essentials to low-income individuals and families in Ottawa.",
        targetFocusPopulation = "Low-income families, unemployed individuals",
        scheduleRecurrence = "Mon-Fri, 8 AM - 6 PM",
        registrationInfo = "Referral required, some items free, others may require token.",
        accessibilityTransportation = "Public transit available, parking on-site.",
        volunteeringOpportunities = "No volunteering opportunities"
    )

    val asset3 = Asset(
        name = "Ottawa Library - Main Branch",
        category = "Education, Community, Culture",
        latitude = 45.4215,
        longitude = -75.6925,
        address = "120 Metcalfe Street, Ottawa, ON",
        contactInfo = "(613) 580-2940",
        approvalStatus = true,
        socialWorkerNotes = "Available for educational and recreational activities.",
        relatedServicesLinks = "Other library branches and community centers nearby.",
        description = "Offers free access to books, e-resources, and educational programs for all age groups.",
        targetFocusPopulation = "All age groups, students, job seekers",
        scheduleRecurrence = "Mon-Sat, 9 AM - 8 PM; Sun, 12 PM - 5 PM",
        registrationInfo = "Library card required for borrowing, free to register.",
        accessibilityTransportation = "Wheelchair accessible, nearby bus stops.",
        volunteeringOpportunities = "No"
    )

    val asset4 = Asset(
        name = "The Good Companions Centre",
        category = "Health, Social, Support",
        latitude = 45.3955,
        longitude = -75.7035,
        address = "670 Albert Street, Ottawa, ON",
        contactInfo = "(613) 236-0428",
        approvalStatus = true,
        socialWorkerNotes = "Offers programs tailored for seniors and individuals with mobility challenges.",
        relatedServicesLinks = "Nearby senior housing and care facilities.",
        description = "A community center offering programs and services for older adults, including health workshops and social activities.",
        targetFocusPopulation = "Senior citizens, caregivers",
        scheduleRecurrence = "Mon-Fri, 9 AM - 4 PM",
        registrationInfo = "Free to join, some workshops require registration.",
        accessibilityTransportation = "Wheelchair accessible, accessible parking available.",
        volunteeringOpportunities = "Yes, please call"
    )

    val asset5 = Asset(
        name = "Ottawa Rideau Transit Service",
        category = "Transportation, Community, Accessibility",
        latitude = 45.4215,
        longitude = -75.6995,
        address = "2301-2305 Riverside Drive, Ottawa, ON",
        contactInfo = "(613) 741-4390",
        approvalStatus = true,
        socialWorkerNotes = "Provides accessible transit options for individuals with disabilities.",
        relatedServicesLinks = "Nearby bus routes and bike-sharing services.",
        description = "Offers accessible public transit services throughout Ottawa for individuals with mobility needs and general public.",
        targetFocusPopulation = "Individuals with disabilities, general public",
        scheduleRecurrence = "24/7 service; frequency varies by route",
        registrationInfo = "Pre-registration required for some accessibility services, regular fares apply.",
        accessibilityTransportation = "Fully accessible, support for wheelchairs and scooters.",
        volunteeringOpportunities = null
    )
}