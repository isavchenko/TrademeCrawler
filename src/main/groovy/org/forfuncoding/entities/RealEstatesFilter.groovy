package org.forfuncoding.entities

/**
 * Filter for real estates
 */
class RealEstatesFilter {
    def city = 1 //auckland
    def area = 7 //auckland city
    def suburbs = ["Ponsonby": 83, "Parnell": 41, "Eden terrace": 135, "Newmarket": 99, "Mount eden": 90, "Ramuera": 40, "Mission Bay": 62]
    def propertyType = "Unit"
    def bedsCount = 2
    def maxPrice = 45000
}
