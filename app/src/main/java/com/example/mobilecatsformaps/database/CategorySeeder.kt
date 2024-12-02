package com.example.mobilecatsformaps.database

class CategorySeeder(private val categoryDao: CategoryDao) {
    suspend fun seedCategories() {
        // Insert top-level categories and get their IDs
        val administrativeSupportId = categoryDao.insertCategory(Category(name = "Administrative support"))
        val communityEventsId = categoryDao.insertCategory(Category(name = "Community events"))
        val transportationId = categoryDao.insertCategory(Category(name = "Transportation"))
        val communityOrganizationsId = categoryDao.insertCategory(Category(name = "Community organizations"))
        val communityPlacesId = categoryDao.insertCategory(Category(name = "Community places"))
        val healthServicesId = categoryDao.insertCategory(Category(name = "Health Services"))
        val socialServicesId = categoryDao.insertCategory(Category(name = "Social Services"))
        val foodSecurityId = categoryDao.insertCategory(Category(name = "Food Security"))
        val basicNeedsId = categoryDao.insertCategory(Category(name = "Basic Needs"))
        val hobbyId = categoryDao.insertCategory(Category(name = "Hobby"))
        val sportAndRecreationId = categoryDao.insertCategory(Category(name = "Sport and recreation"))
        val privateServicesId = categoryDao.insertCategory(Category(name = "Private services", parentCategoryId = communityPlacesId))
        // Insert subcategories with their parent category IDs
        val subcategories = listOf(
            // Administrative support subcategories
            Category(name = "Skill training", parentCategoryId = administrativeSupportId),
            Category(name = "Employment", parentCategoryId = administrativeSupportId),
            Category(name = "Housing", parentCategoryId = administrativeSupportId),
            Category(name = "Childcare", parentCategoryId = administrativeSupportId),
            Category(name = "Legal", parentCategoryId = administrativeSupportId),
            Category(name = "Income tax", parentCategoryId = administrativeSupportId),
            Category(name = "ESL", parentCategoryId = administrativeSupportId),

            // Community events
            Category(name = "Community events", parentCategoryId = communityEventsId),

            // Transportation
            Category(name = "Transportation", parentCategoryId = transportationId),

            // Community organizations subcategories
            Category(name = "Government representatives", parentCategoryId = communityOrganizationsId),
            Category(name = "Charitable organizations", parentCategoryId = communityOrganizationsId),
            Category(name = "Community and Recreational Centers", parentCategoryId = communityOrganizationsId),
            Category(name = "Libraries", parentCategoryId = communityOrganizationsId),
            Category(name = "Health organizations", parentCategoryId = communityOrganizationsId),
            Category(name = "Governmental web-services", parentCategoryId = communityOrganizationsId),

            // Community places subcategories
            Category(name = "Parks", parentCategoryId = communityPlacesId),
            Category(name = "Green Spaces", parentCategoryId = communityPlacesId),
            Category(name = "Playgrounds", parentCategoryId = communityPlacesId),
            Category(name = "Schools", parentCategoryId = communityPlacesId),


            Category(name = "Bike repair", parentCategoryId = privateServicesId),
            Category(name = "Computer café", parentCategoryId = privateServicesId),
            Category(name = "Car-sharing and similar transportation sharing services", parentCategoryId = privateServicesId),
            Category(name = "Rentals of gear or tools", parentCategoryId = privateServicesId),

            // Health Services subcategories
            Category(name = "Mental Health", parentCategoryId = healthServicesId),
            Category(name = "Primary Health Care", parentCategoryId = healthServicesId),
            Category(name = "Harm Reduction", parentCategoryId = healthServicesId),
            Category(name = "Counselling", parentCategoryId = healthServicesId),
            Category(name = "Walk-in Counselling", parentCategoryId = healthServicesId),
            Category(name = "Pregnancy", parentCategoryId = healthServicesId),
            Category(name = "Sexual Health", parentCategoryId = healthServicesId),
            Category(name = "Support Group", parentCategoryId = healthServicesId),
            Category(name = "Crisis", parentCategoryId = healthServicesId),
            Category(name = "Home support", parentCategoryId = healthServicesId),
            Category(name = "Social prescribing", parentCategoryId = healthServicesId),

            // Social Services subcategories
            Category(name = "Community Organization", parentCategoryId = socialServicesId),
            Category(name = "Crisis intake", parentCategoryId = socialServicesId),
            Category(name = "Peer support", parentCategoryId = socialServicesId),
            Category(name = "Family Peer Support", parentCategoryId = socialServicesId),
            Category(name = "Drop-in programming", parentCategoryId = socialServicesId),
            Category(name = "Day programming", parentCategoryId = socialServicesId),
            Category(name = "Playgroups", parentCategoryId = socialServicesId),

            // Food Security subcategories
            Category(name = "Baby Cupboard", parentCategoryId = foodSecurityId),
            Category(name = "Breakfast program", parentCategoryId = foodSecurityId),
            Category(name = "Lunch program", parentCategoryId = foodSecurityId),
            Category(name = "Emergency Food", parentCategoryId = foodSecurityId),
            Category(name = "Food Bank", parentCategoryId = foodSecurityId),
            Category(name = "Food Cupboard", parentCategoryId = foodSecurityId),

            // Basic Needs subcategories
            Category(name = "Clothing", parentCategoryId = basicNeedsId),
            Category(name = "Furniture", parentCategoryId = basicNeedsId),
            Category(name = "Donations", parentCategoryId = basicNeedsId),
            Category(name = "Hygiene products", parentCategoryId = basicNeedsId),

            // Hobby subcategories
            Category(name = "Cards & Games", parentCategoryId = hobbyId),
            Category(name = "Arts & Crafts", parentCategoryId = hobbyId),
            Category(name = "Knitting & Sewing", parentCategoryId = hobbyId),
            Category(name = "Gardening", parentCategoryId = hobbyId),
            Category(name = "Nature", parentCategoryId = hobbyId),
            Category(name = "Cooking class", parentCategoryId = hobbyId),
            Category(name = "Music", parentCategoryId = hobbyId),
            Category(name = "Singing", parentCategoryId = hobbyId),
            Category(name = "Drama", parentCategoryId = hobbyId),
            Category(name = "Dance", parentCategoryId = hobbyId),
            Category(name = "Outdoors", parentCategoryId = hobbyId),
            Category(name = "Book clubs", parentCategoryId = hobbyId),
            Category(name = "Social clubs", parentCategoryId = hobbyId),

            // Sport and recreation subcategories
            Category(name = "Walking groups", parentCategoryId = sportAndRecreationId),
            Category(name = "Soccer", parentCategoryId = sportAndRecreationId),
            Category(name = "Swimming", parentCategoryId = sportAndRecreationId),
            Category(name = "Sports", parentCategoryId = sportAndRecreationId),
            Category(name = "Athletics", parentCategoryId = sportAndRecreationId),
            Category(name = "Exercise", parentCategoryId = sportAndRecreationId),
            Category(name = "Yoga", parentCategoryId = sportAndRecreationId),
            Category(name = "Martial arts", parentCategoryId = sportAndRecreationId),
            Category(name = "Gym", parentCategoryId = sportAndRecreationId)
        )

        // Insert subcategories
        subcategories.forEach { category ->
            categoryDao.insertCategory(category)
        }
    }
}