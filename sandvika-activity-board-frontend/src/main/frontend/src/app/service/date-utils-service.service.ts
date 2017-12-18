import {Injectable} from '@angular/core';

@Injectable()
export class DateUtilsServiceService {

    constructor() {
    }

    static getWeekNumber(thatDate): number {
        // Copy date so don't modify original
        thatDate = new Date(+thatDate);
        thatDate.setHours(0, 0, 0, 0);

        // Set to nearest Thursday: current date + 4 - current day number
        // Make Sunday's day number 7
        thatDate.setDate(thatDate.getDate() + 4 - (thatDate.getDay() || 7));

        // Get first day of year
        let yearStart = new Date(thatDate.getFullYear(), 0, 1);

        // Calculate full weeks to nearest Thursday
        // Return array of year and week number
        // 86400000 = 1* 24 * 60 * 60 * 1000 = millis in one day
        return Math.ceil((((thatDate.valueOf() - yearStart.valueOf()) / 86400000) + 1) / 7);
    }
}
