export class Activity {
    "id": Number;
    "points": Number;
    "name": String;
    "athleteLastName": String;
    "athletefirstName": String;
    "description": String;
    "distanceInMeters": Number;
    "movingTimeInSeconds": Number;
    "elapsedTimeInSeconds": Number;
    "totalElevationGaininMeters": Number;
    "type": String;
    "sufferScore": Number;
}

export module Activity {
    export class startDateLocal {
        "year": Number;
        "month": String;
        "dayOfMonth": Number;
        "dayOfWeek": String;
        // "hour": 8;
        // "dayOfYear": 52;
        // "minute": 3;
        // "second": 28;
        // "nano": 0;
        "monthValue": Number;
    }

    export module startDateLocal {
        export class Chronology {
            "calendarType": String;
            "id": String
        }
    }
}