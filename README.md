<h1 align=center>Car sharing service</h1>
<h2 align=center>  Description</h2>

The Car Rental Management System is an online platform designed to streamline the operations of a car sharing service. 
This system replaces the outdated manual process of managing car rentals, payments, and tracking with a modern, automated solution. 
It aims to enhance the user experience and provide efficient management tools for the service administrators.

<h2 align=center>Key features</h2>
<p>1. User Registration and Authentication: Users can create accounts and log in securely to access the car rental service. 
This ensures that only authorized individuals can rent cars and make transactions.</p>

<p>2. Car Inventory: The system maintains a comprehensive inventory of available cars, including their models, availability status, location, and rental history. </p>

<p>3. Online Booking: Users can check the availability of cars in real-time and make reservations conveniently through the platform. 
The system ensures that only available cars can be booked, reducing the chance of conflicts.</p>

<p>4. Rental Duration and Pricing: The system enables users to select the desired rental duration and calculates the corresponding price automatically.</p>

<p>5. Payment Integration: Users can make secure online payments using various methods, including credit cards, debit cards, and digital wallets. 
The system ensures the privacy and security of user payment information and provides transaction records for reference.</p>

<p>6. Notifications and Reminders: The system sends notifications to users regarding their upcoming reservations, rental durations, and payment reminders. 
This helps users stay informed and ensures a smooth rental experience.</p>

<h2 align=center>Technologies </h2>
<code>Java 17</code> |
<code>Spring Boot</code> |
<code>Spirng Boot Dev Tools</code> |
<code>Spring Web</code> |
<code>Spring Security</code> |
<code>Spring Data JPA</code> |
<code>Swagger</code> |
<code>Lombok</code> |
<code>Mapstruct</code> |
<code>Liquibase</code> |
<code>MySQL</code> |
<code>Docker</code> |
<code>Stripe</code>

<h2 align=center>Project Launch with Docker</h2>
<p>1.Install Docker if you don't have (here is the link https://www.docker.com/products/docker-desktop/)</p>
<p>2. Clone the repository from GitHub</p>
<p>3.Create a .env file with the necessary environment variables</p>
<p>4.Run mvn clean package command</p>
<p>5.Run docker-compose up --build command to build and start the Docker containers</p>
<p>6.The application should be running at http://localhost:6868. You can test the operation of the application using swagger http://localhost:8081/swagger-ui/index.html#/.</p>
