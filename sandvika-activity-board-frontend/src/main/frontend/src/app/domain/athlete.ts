export class Athlete {
    id: string;
    lastName: string;
    firstName: string;
    handicapList: Handicap[];
    profile: string;
    token: string;
    club: string;
}

export class Handicap {
    handicap: Number;
    timestamp: Date;
}

export class ModalAthlete {
    "athleteId": Number;
    "athleteLastName": string;
    "athleteFirstName": string;
    "points": Number;
    "lastActivityDate": string;
    "numberOfActivities": Number;
    "kilometers": Number;
    "minutes": Number;
    "handicap": Number;
}