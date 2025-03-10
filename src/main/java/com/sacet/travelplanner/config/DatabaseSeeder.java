package com.sacet.travelplanner.config;

import com.sacet.travelplanner.model.Destination;
import com.sacet.travelplanner.model.Hotel;
import com.sacet.travelplanner.model.Itinerary;
import com.sacet.travelplanner.repository.DestinationRepository;
import com.sacet.travelplanner.repository.HotelRepository;
import com.sacet.travelplanner.repository.ItineraryRepository;
import com.sacet.travelplanner.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ItineraryRepository itineraryRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    @Transactional
    public void run(String... args) {
        // Clear existing data in correct order to respect foreign key constraints
        bookingRepository.deleteAll();
        itineraryRepository.deleteAll();
        hotelRepository.deleteAll();
        destinationRepository.deleteAll();

        // Create Destinations
        Destination goa = new Destination();
        goa.setName("Goa");
        goa.setCountry("India");
        goa.setDescription("Goa is a state on India's western coast known for its beaches, nightlife, places of worship, and world heritage architecture. A blend of Indian and Portuguese cultures, Goa offers a unique experience with its rich history and natural beauty.");
        goa.setBestTimeToVisit("November to February");
        goa.setThingsToSee("Calangute Beach, Basilica of Bom Jesus, Fort Aguada, Dudhsagar Falls");
        goa.setImageUrl("https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        goa.setEstimatedBudget(25000.0);
        goa.setTripType("relaxation");
        goa.setAttractions("Beautiful beaches, Portuguese architecture, vibrant nightlife, water sports");
        goa.setWeatherInfo("Tropical climate with temperatures between 25-32°C. Monsoon from June to September.");
        goa.setTravelRoutes("Accessible via Dabolim Airport, train stations at Madgaon and Vasco da Gama");
        goa.setRecommendedDuration(5);
        destinationRepository.save(goa);

        Destination pondicherry = new Destination();
        pondicherry.setName("Pondicherry");
        pondicherry.setCountry("India");
        pondicherry.setDescription("Pondicherry (now Puducherry) is a French colonial settlement in India known for its French Quarter, spiritual atmosphere, and beautiful beaches. The city offers a perfect blend of French and Tamil cultures.");
        pondicherry.setBestTimeToVisit("October to March");
        pondicherry.setThingsToSee("French Quarter, Auroville, Paradise Beach, Sri Aurobindo Ashram");
        pondicherry.setImageUrl("https://images.unsplash.com/photo-1582510003544-4d00b7f74220?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        pondicherry.setEstimatedBudget(12000.0);
        pondicherry.setTripType("cultural");
        pondicherry.setAttractions("French architecture, spiritual centers, beaches, French cuisine");
        pondicherry.setWeatherInfo("Tropical climate with temperatures between 22-35°C. Best avoided during summer (April-June).");
        pondicherry.setTravelRoutes("Accessible via Chennai Airport (150km), well-connected by road and rail");
        pondicherry.setRecommendedDuration(3);
        destinationRepository.save(pondicherry);

        Destination manali = new Destination();
        manali.setName("Manali");
        manali.setCountry("India");
        manali.setDescription("Manali is a high-altitude Himalayan resort town in Himachal Pradesh, known for its snow-capped mountains, adventure sports, and scenic beauty. Perfect for both adventure seekers and peace lovers.");
        manali.setBestTimeToVisit("December to February for snow, March to June for pleasant weather");
        manali.setThingsToSee("Rohtang Pass, Solang Valley, Hadimba Temple, Old Manali");
        manali.setImageUrl("https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        manali.setEstimatedBudget(33000.0);
        manali.setTripType("adventure");
        manali.setAttractions("Snow sports, trekking, paragliding, river rafting, ancient temples");
        manali.setWeatherInfo("Cold climate with temperatures between -1°C to 25°C. Heavy snowfall in winter.");
        manali.setTravelRoutes("Accessible via Bhuntar Airport (50km), well-connected by road from Delhi");
        manali.setRecommendedDuration(6);
        destinationRepository.save(manali);

        Destination kashmir = new Destination();
        kashmir.setName("Kashmir");
        kashmir.setCountry("India");
        kashmir.setDescription("Kashmir is known as 'Paradise on Earth' for its stunning natural beauty, featuring snow-capped mountains, serene lakes, and beautiful gardens. The region offers a unique blend of natural beauty and cultural richness.");
        kashmir.setBestTimeToVisit("March to October");
        kashmir.setThingsToSee("Dal Lake, Gulmarg, Pahalgam, Sonmarg, Mughal Gardens");
        kashmir.setImageUrl("https://images.unsplash.com/photo-1566837945700-30057527ade0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        kashmir.setEstimatedBudget(52500.0);
        kashmir.setTripType("family");
        kashmir.setAttractions("Shikara rides, Gondola rides, skiing, beautiful gardens, handicrafts");
        kashmir.setWeatherInfo("Alpine climate with cold winters (-2°C to 12°C) and mild summers (15°C to 30°C)");
        kashmir.setTravelRoutes("Accessible via Srinagar Airport, connected by road and rail");
        kashmir.setRecommendedDuration(7);
        destinationRepository.save(kashmir);

        // Create Hotels
        Hotel goaMarriott = new Hotel();
        goaMarriott.setName("Goa Marriott Resort & Spa");
        goaMarriott.setLocation("Goa");
        goaMarriott.setDescription("Luxury beachfront resort with stunning views of the Arabian Sea");
        goaMarriott.setPricePerNight(15000.0);
        goaMarriott.setRating(4.8);
        goaMarriott.setImageUrl("https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        goaMarriott.setAmenities("Spa, Swimming Pool, Multiple Restaurants, Beach Access");
        goaMarriott.setRoomTypes("Deluxe Room, Sea View Room, Suite");
        goaMarriott.setHasWifi(true);
        goaMarriott.setHasParking(true);
        goaMarriott.setHasPool(true);
        goaMarriott.setHasRestaurant(true);
        goaMarriott.setCheckInTime("14:00");
        goaMarriott.setCheckOutTime("12:00");
        hotelRepository.save(goaMarriott);

        Hotel pondiLeMaison = new Hotel();
        pondiLeMaison.setName("Le Maison Radha");
        pondiLeMaison.setLocation("Pondicherry");
        pondiLeMaison.setDescription("Boutique hotel in French Quarter with colonial architecture");
        pondiLeMaison.setPricePerNight(8000.0);
        pondiLeMaison.setRating(4.5);
        pondiLeMaison.setImageUrl("https://images.unsplash.com/photo-1455587734955-081b22074882?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        pondiLeMaison.setAmenities("Heritage Building, Garden, French Restaurant");
        pondiLeMaison.setRoomTypes("Heritage Room, Garden Room");
        pondiLeMaison.setHasWifi(true);
        pondiLeMaison.setHasParking(true);
        pondiLeMaison.setHasRestaurant(true);
        pondiLeMaison.setCheckInTime("13:00");
        pondiLeMaison.setCheckOutTime("11:00");
        hotelRepository.save(pondiLeMaison);

        Hotel manaliSolang = new Hotel();
        manaliSolang.setName("Solang Valley Resort");
        manaliSolang.setLocation("Manali");
        manaliSolang.setDescription("Luxury mountain resort with panoramic views of the Himalayas");
        manaliSolang.setPricePerNight(12000.0);
        manaliSolang.setRating(4.6);
        manaliSolang.setImageUrl("https://images.unsplash.com/photo-1584132967334-10e028bd69f7?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        manaliSolang.setAmenities("Spa, Adventure Sports, Mountain Views");
        manaliSolang.setRoomTypes("Valley View Room, Luxury Suite, Family Room");
        manaliSolang.setHasWifi(true);
        manaliSolang.setHasParking(true);
        manaliSolang.setHasRestaurant(true);
        manaliSolang.setCheckInTime("14:00");
        manaliSolang.setCheckOutTime("11:00");
        hotelRepository.save(manaliSolang);

        Hotel kashmirTajVivanta = new Hotel();
        kashmirTajVivanta.setName("Taj Vivanta Dal View");
        kashmirTajVivanta.setLocation("Kashmir");
        kashmirTajVivanta.setDescription("Luxury hotel overlooking Dal Lake with stunning mountain views");
        kashmirTajVivanta.setPricePerNight(18000.0);
        kashmirTajVivanta.setRating(4.9);
        kashmirTajVivanta.setImageUrl("https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        kashmirTajVivanta.setAmenities("Lake View, Spa, Multiple Restaurants");
        kashmirTajVivanta.setRoomTypes("Superior Room, Deluxe Suite, Presidential Suite");
        kashmirTajVivanta.setHasWifi(true);
        kashmirTajVivanta.setHasParking(true);
        kashmirTajVivanta.setHasPool(true);
        kashmirTajVivanta.setHasRestaurant(true);
        kashmirTajVivanta.setCheckInTime("14:00");
        kashmirTajVivanta.setCheckOutTime("12:00");
        hotelRepository.save(kashmirTajVivanta);

        // Create additional hotels for more variety
        Hotel goaPark = new Hotel();
        goaPark.setName("Park Hyatt Goa Resort");
        goaPark.setLocation("Goa");
        goaPark.setDescription("Luxurious beachside resort with world-class amenities");
        goaPark.setPricePerNight(20000.0);
        goaPark.setRating(4.7);
        goaPark.setImageUrl("https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        goaPark.setAmenities("Private Beach, Spa, Multiple Pools, Water Sports");
        goaPark.setRoomTypes("Park Room, Deluxe Suite, Presidential Villa");
        goaPark.setHasWifi(true);
        goaPark.setHasParking(true);
        goaPark.setHasPool(true);
        goaPark.setHasRestaurant(true);
        goaPark.setCheckInTime("15:00");
        goaPark.setCheckOutTime("12:00");
        hotelRepository.save(goaPark);

        Hotel pondiPalais = new Hotel();
        pondiPalais.setName("Palais de Mahe");
        pondiPalais.setLocation("Pondicherry");
        pondiPalais.setDescription("Colonial-style luxury hotel in the heart of French Quarter");
        pondiPalais.setPricePerNight(12000.0);
        pondiPalais.setRating(4.6);
        pondiPalais.setImageUrl("https://images.unsplash.com/photo-1566665797739-1674de7a421a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        pondiPalais.setAmenities("Rooftop Pool, Colonial Architecture, Courtyard Restaurant");
        pondiPalais.setRoomTypes("Colonial Room, Luxury Suite");
        pondiPalais.setHasWifi(true);
        pondiPalais.setHasParking(true);
        pondiPalais.setHasPool(true);
        pondiPalais.setHasRestaurant(true);
        pondiPalais.setCheckInTime("14:00");
        pondiPalais.setCheckOutTime("11:00");
        hotelRepository.save(pondiPalais);

        // Additional Goa Hotels
        Hotel goaNovotel = new Hotel();
        goaNovotel.setName("Novotel Goa Candolim");
        goaNovotel.setLocation("Goa");
        goaNovotel.setDescription("Modern resort near Candolim Beach with contemporary amenities");
        goaNovotel.setPricePerNight(10000.0);
        goaNovotel.setRating(4.4);
        goaNovotel.setImageUrl("https://images.unsplash.com/photo-1582719508461-905c673771fd?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        goaNovotel.setAmenities("Swimming Pool, Spa, Beach Access, Kids Club");
        goaNovotel.setRoomTypes("Superior Room, Deluxe Room, Executive Suite");
        goaNovotel.setHasWifi(true);
        goaNovotel.setHasParking(true);
        goaNovotel.setHasPool(true);
        goaNovotel.setHasRestaurant(true);
        goaNovotel.setCheckInTime("14:00");
        goaNovotel.setCheckOutTime("12:00");
        hotelRepository.save(goaNovotel);

        Hotel goaResort = new Hotel();
        goaResort.setName("W Goa");
        goaResort.setLocation("Goa");
        goaResort.setDescription("Luxury beachfront resort with modern design and vibrant nightlife");
        goaResort.setPricePerNight(25000.0);
        goaResort.setRating(4.7);
        goaResort.setImageUrl("https://images.unsplash.com/photo-1578683010236-d716f9a3f461?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        goaResort.setAmenities("Private Beach, Spa, Infinity Pool, Multiple Restaurants");
        goaResort.setRoomTypes("Wonderful Room, Spectacular Room, Marvelous Suite");
        goaResort.setHasWifi(true);
        goaResort.setHasParking(true);
        goaResort.setHasPool(true);
        goaResort.setHasRestaurant(true);
        goaResort.setCheckInTime("15:00");
        goaResort.setCheckOutTime("12:00");
        hotelRepository.save(goaResort);

        // Additional Pondicherry Hotels
        Hotel pondiPromenade = new Hotel();
        pondiPromenade.setName("The Promenade");
        pondiPromenade.setLocation("Pondicherry");
        pondiPromenade.setDescription("Seaside boutique hotel with colonial charm and modern comforts");
        pondiPromenade.setPricePerNight(9000.0);
        pondiPromenade.setRating(4.3);
        pondiPromenade.setImageUrl("https://images.unsplash.com/photo-1561501900-3701fa6a0864?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        pondiPromenade.setAmenities("Sea View, Rooftop Restaurant, Heritage Architecture");
        pondiPromenade.setRoomTypes("Deluxe Room, Sea View Room, Suite");
        pondiPromenade.setHasWifi(true);
        pondiPromenade.setHasParking(true);
        pondiPromenade.setHasRestaurant(true);
        pondiPromenade.setCheckInTime("14:00");
        pondiPromenade.setCheckOutTime("11:00");
        hotelRepository.save(pondiPromenade);

        Hotel pondiVilla = new Hotel();
        pondiVilla.setName("Villa Shanti");
        pondiVilla.setLocation("Pondicherry");
        pondiVilla.setDescription("Restored heritage villa with modern amenities in French Quarter");
        pondiVilla.setPricePerNight(7000.0);
        pondiVilla.setRating(4.4);
        pondiVilla.setImageUrl("https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        pondiVilla.setAmenities("Courtyard Garden, Heritage Architecture, Restaurant");
        pondiVilla.setRoomTypes("Standard Room, Deluxe Room, Garden Suite");
        pondiVilla.setHasWifi(true);
        pondiVilla.setHasParking(true);
        pondiVilla.setHasRestaurant(true);
        pondiVilla.setCheckInTime("13:00");
        pondiVilla.setCheckOutTime("11:00");
        hotelRepository.save(pondiVilla);

        // Additional Manali Hotels
        Hotel manaliHimalayanResort = new Hotel();
        manaliHimalayanResort.setName("The Himalayan Resort");
        manaliHimalayanResort.setLocation("Manali");
        manaliHimalayanResort.setDescription("Luxury resort with stunning mountain views and traditional architecture");
        manaliHimalayanResort.setPricePerNight(15000.0);
        manaliHimalayanResort.setRating(4.7);
        manaliHimalayanResort.setImageUrl("https://images.unsplash.com/photo-1585543805890-6051f7829f98?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        manaliHimalayanResort.setAmenities("Mountain Views, Spa, Adventure Sports Desk");
        manaliHimalayanResort.setRoomTypes("Luxury Room, Mountain View Suite, Royal Suite");
        manaliHimalayanResort.setHasWifi(true);
        manaliHimalayanResort.setHasParking(true);
        manaliHimalayanResort.setHasRestaurant(true);
        manaliHimalayanResort.setCheckInTime("14:00");
        manaliHimalayanResort.setCheckOutTime("11:00");
        hotelRepository.save(manaliHimalayanResort);

        Hotel manaliWhiteHaven = new Hotel();
        manaliWhiteHaven.setName("White Haven Resort");
        manaliWhiteHaven.setLocation("Manali");
        manaliWhiteHaven.setDescription("Boutique resort offering cozy stays with snow-capped mountain views");
        manaliWhiteHaven.setPricePerNight(8000.0);
        manaliWhiteHaven.setRating(4.3);
        manaliWhiteHaven.setImageUrl("https://images.unsplash.com/photo-1601918774946-25832a4be0d6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        manaliWhiteHaven.setAmenities("Bonfire Area, Adventure Activities, Mountain Views");
        manaliWhiteHaven.setRoomTypes("Deluxe Room, Premium Room, Family Suite");
        manaliWhiteHaven.setHasWifi(true);
        manaliWhiteHaven.setHasParking(true);
        manaliWhiteHaven.setHasRestaurant(true);
        manaliWhiteHaven.setCheckInTime("13:00");
        manaliWhiteHaven.setCheckOutTime("11:00");
        hotelRepository.save(manaliWhiteHaven);

        // Additional Kashmir Hotels
        Hotel kashmirLalit = new Hotel();
        kashmirLalit.setName("The Lalit Grand Palace");
        kashmirLalit.setLocation("Kashmir");
        kashmirLalit.setDescription("Heritage palace hotel with luxury amenities and panoramic views");
        kashmirLalit.setPricePerNight(20000.0);
        kashmirLalit.setRating(4.8);
        kashmirLalit.setImageUrl("https://images.unsplash.com/photo-1600011689032-8b628b8a8747?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        kashmirLalit.setAmenities("Palace Gardens, Spa, Lake Views, Heritage Tours");
        kashmirLalit.setRoomTypes("Palace Room, Royal Suite, Presidential Suite");
        kashmirLalit.setHasWifi(true);
        kashmirLalit.setHasParking(true);
        kashmirLalit.setHasPool(true);
        kashmirLalit.setHasRestaurant(true);
        kashmirLalit.setCheckInTime("14:00");
        kashmirLalit.setCheckOutTime("12:00");
        hotelRepository.save(kashmirLalit);

        Hotel kashmirKhyber = new Hotel();
        kashmirKhyber.setName("The Khyber Himalayan Resort & Spa");
        kashmirKhyber.setLocation("Kashmir");
        kashmirKhyber.setDescription("Luxury mountain resort in Gulmarg with world-class amenities");
        kashmirKhyber.setPricePerNight(25000.0);
        kashmirKhyber.setRating(4.9);
        kashmirKhyber.setImageUrl("https://images.unsplash.com/photo-1595274459742-4a41d35784ee?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1000&q=80");
        kashmirKhyber.setAmenities("Ski Access, Luxury Spa, Mountain Views, Multiple Restaurants");
        kashmirKhyber.setRoomTypes("Premier Room, Luxury Room, Presidential Suite");
        kashmirKhyber.setHasWifi(true);
        kashmirKhyber.setHasParking(true);
        kashmirKhyber.setHasPool(true);
        kashmirKhyber.setHasRestaurant(true);
        kashmirKhyber.setCheckInTime("14:00");
        kashmirKhyber.setCheckOutTime("12:00");
        hotelRepository.save(kashmirKhyber);

        // Create Itineraries
        // Goa Itinerary - 5 days
        createItinerary(goa, 1, 
            "Visit Calangute Beach, water sports activities",
            "Explore Fort Aguada, lunch at beach shack",
            "Sunset cruise on Mandovi River, dinner at Thalassa",
            "Beach Resort",
            "Taxi or rent a bike",
            "Local Goan cuisine, seafood",
            5000.0);

        createItinerary(goa, 2,
            "Visit Basilica of Bom Jesus, Old Goa churches",
            "Spice plantation tour with traditional lunch",
            "Evening at Baga Beach, nightlife exploration",
            "Beach Resort",
            "Taxi or rent a bike",
            "Portuguese-influenced Goan cuisine",
            4500.0);

        createItinerary(goa, 3,
            "Morning yoga on the beach",
            "Visit to Dudhsagar Falls",
            "Dinner and casino visit",
            "Beach Resort",
            "Taxi and train for falls",
            "Local and Continental cuisine",
            5500.0);

        createItinerary(goa, 4,
            "Visit to Butterfly Beach",
            "Water sports at Palolem Beach",
            "Beachside BBQ dinner",
            "Beach Resort",
            "Taxi or rent a bike",
            "Seafood specialties",
            5000.0);

        createItinerary(goa, 5,
            "Visit to Saturday Night Market",
            "Beach hopping - Anjuna and Vagator",
            "Sunset party at Curlies",
            "Beach Resort",
            "Taxi or rent a bike",
            "Street food and beach cafe cuisine",
            5000.0);

        // Pondicherry Itinerary - 3 days
        createItinerary(pondicherry, 1,
            "Visit Sri Aurobindo Ashram, walk in French Quarter",
            "Explore Auroville, visit Matri Mandir",
            "Evening at Rock Beach, dinner at La Villa",
            "Heritage Hotel",
            "Bicycle or walking",
            "French and Tamil cuisine",
            4000.0);

        createItinerary(pondicherry, 2,
            "Morning yoga session, Paradise Beach visit",
            "Goubert Market exploration, handicraft shopping",
            "Sunset at Promenade Beach, dinner at Carte Blanche",
            "Heritage Hotel",
            "Bicycle or walking",
            "Indo-French fusion cuisine",
            4000.0);

        createItinerary(pondicherry, 3,
            "Visit to Botanical Gardens",
            "Explore Tamil Quarter and temples",
            "Evening food tour in the French Quarter",
            "Heritage Hotel",
            "Bicycle or walking",
            "Mix of French and Tamil street food",
            4000.0);

        // Manali Itinerary - 6 days
        createItinerary(manali, 1,
            "Visit Hadimba Temple, explore Old Manali",
            "Adventure activities at Solang Valley",
            "Mall Road shopping, dinner at Johnson's Cafe",
            "Mountain Resort",
            "Local taxi",
            "Himachali cuisine, international options",
            5500.0);

        createItinerary(manali, 2,
            "Trekking to Jogini Falls",
            "River rafting in Beas River",
            "Bonfire and cultural evening",
            "Mountain Resort",
            "Local taxi",
            "Traditional mountain cuisine",
            5500.0);

        createItinerary(manali, 3,
            "Day trip to Rohtang Pass",
            "Snow activities and photography",
            "Evening at the Mall Road",
            "Mountain Resort",
            "Local taxi",
            "Local and Continental cuisine",
            5500.0);

        createItinerary(manali, 4,
            "Visit to Naggar Castle",
            "Art Gallery and Roerich Art Gallery",
            "Traditional Himachali dinner",
            "Mountain Resort",
            "Local taxi",
            "Traditional Himachali thali",
            5500.0);

        createItinerary(manali, 5,
            "Paragliding at Solang Valley",
            "Visit to Vashisht Hot Springs",
            "Shopping for local handicrafts",
            "Mountain Resort",
            "Local taxi",
            "Mix of local and international cuisine",
            5500.0);

        createItinerary(manali, 6,
            "Nature walk in Van Vihar",
            "Visit to Buddhist monasteries",
            "Farewell dinner at local restaurant",
            "Mountain Resort",
            "Local taxi",
            "Special mountain delicacies",
            5500.0);

        // Kashmir Itinerary - 7 days
        createItinerary(kashmir, 1,
            "Shikara ride on Dal Lake, visit Mughal Gardens",
            "Day trip to Gulmarg, Gondola ride",
            "Shopping for handicrafts, dinner at Ahdoos",
            "Lake View Hotel",
            "Private taxi",
            "Wazwan, Kashmiri cuisine",
            7500.0);

        createItinerary(kashmir, 2,
            "Visit Shankaracharya Temple",
            "Pahalgam day trip, horse riding",
            "Evening at Dal Lake, traditional dinner",
            "Lake View Hotel",
            "Private taxi",
            "Traditional Kashmiri cuisine",
            7500.0);

        createItinerary(kashmir, 3,
            "Visit to Sonamarg",
            "Thajiwas Glacier trek",
            "Evening at local market",
            "Lake View Hotel",
            "Private taxi",
            "Mix of local and continental cuisine",
            7500.0);

        createItinerary(kashmir, 4,
            "Visit to Yusmarg",
            "Picnic in the meadows",
            "Cultural show and dinner",
            "Lake View Hotel",
            "Private taxi",
            "Traditional Kashmiri dinner",
            7500.0);

        createItinerary(kashmir, 5,
            "Visit to Dachigam National Park",
            "Wildlife spotting and nature walk",
            "Evening at Boulevard Road",
            "Lake View Hotel",
            "Private taxi",
            "Local specialties",
            7500.0);

        createItinerary(kashmir, 6,
            "Visit to Pari Mahal",
            "Explore Nishat and Shalimar Gardens",
            "Evening Shikara ride with dinner",
            "Lake View Hotel",
            "Private taxi",
            "Royal Wazwan experience",
            7500.0);

        createItinerary(kashmir, 7,
            "Local market exploration",
            "Visit to Hazratbal Shrine",
            "Farewell dinner cruise on Dal Lake",
            "Lake View Hotel",
            "Private taxi",
            "Special Kashmiri feast",
            7500.0);
    }

    private void createItinerary(Destination destination, int dayNumber,
                               String morning, String afternoon, String evening,
                               String accommodation, String transport, String meals,
                               Double cost) {
        // Check if an itinerary already exists for this destination and day number
        List<Itinerary> existingItineraries = itineraryRepository.findByDestinationIdOrderByDayNumber(destination.getId());
        boolean exists = existingItineraries.stream()
                .anyMatch(i -> i.getDayNumber().equals(dayNumber));
        
        if (!exists) {
            Itinerary itinerary = new Itinerary();
            itinerary.setDestination(destination);
            itinerary.setDayNumber(dayNumber);
            itinerary.setMorningActivities(morning);
            itinerary.setAfternoonActivities(afternoon);
            itinerary.setEveningActivities(evening);
            itinerary.setSuggestedAccommodation(accommodation);
            itinerary.setTransportationDetails(transport);
            itinerary.setMealSuggestions(meals);
            itinerary.setEstimatedDailyCost(cost);
            itineraryRepository.save(itinerary);
        }
    }
} 