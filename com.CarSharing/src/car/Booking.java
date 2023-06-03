package car;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import app.*;
/**
 * The Booking class represents a booking made by a user for a car.
 */
public class Booking {
    private LocalDate startDate;
    private LocalDate endDate;
    private double dailyRate;
    private Car car;
    private User user;
    /**
     * Constructs a Booking object with the specified start date, end date, daily rate, car, and user.
     * @param startDate the start date of the booking
     * @param endDate the end date of the booking
     * @param dailyRate the daily rate of the car
     * @param car the car being booked
     * @param user the user making the booking
     */
    public Booking(LocalDate startDate, LocalDate endDate, double dailyRate, Car car, User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyRate = dailyRate;
        this.car = car;
        this.user = user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public Car getCar() {
        return car;
    }

    public User getUser() {
        return user;
    }

    public long getDuration() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public double getTotalCost() {
        long duration = getDuration();
        return duration * dailyRate;
    }
}
