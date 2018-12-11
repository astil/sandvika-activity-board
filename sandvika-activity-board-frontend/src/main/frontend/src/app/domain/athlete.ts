export class Athlete {
    id: string;
    lastName: string;
    firstName: string;
    handicapList: Handicap[];
    profile: string;
    token: string;
    clubs: string[];
    defaultClub: string;

}

export class Handicap {
    handicap: number;
    timestamp: Date;
}

export class ModalAthlete {
    'athleteId': number;
    'athleteLastName': string;
    'athleteFirstName': string;
    'points': number;
    'lastActivityDate': string;
    'numberOfActivities': number;
    'kilometers': number;
    'minutes': number;
    'handicap': number;
    'ranking': number;
    'change': number;
}
