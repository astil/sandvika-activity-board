export class Photo {
    private activitySummary: ActivitySummary;
    private url: string;
}

export class ActivitySummary {
    id: Number;
    points: Number;
    name: String;
    athleteLastName: String;
    athletefirstName: String;
    description: String;
    distanceInMeters: Number;
    movingTimeInSeconds: Number;
    elapsedTimeInSeconds: Number;
    totalElevationGaininMeters: Number;
    type: String;
    startDateLocal: Date;
}