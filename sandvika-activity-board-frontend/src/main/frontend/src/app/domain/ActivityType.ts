export class ActivityType {
  code: string;
  decode: string;

  constructor(code: string, decode: string) {
    this.code = code;
    this.decode = decode;
  }
}

export let ACTIVITY_TYPES = [
  new ActivityType('All', 'Alle'),
  new ActivityType('Run', 'Løping'),
  new ActivityType('Ride', 'Sykling'),
  new ActivityType('Swim', 'Svømming'),
  new ActivityType('NordicSki', 'Langrenn'),
  new ActivityType('Rowing', 'Roing'),
  new ActivityType('Walk', 'Gåing'),
  new ActivityType('Hike', 'Hike'),
  new ActivityType('Workout', 'Workout'),
  new ActivityType('WeightTraining', 'Styrke'),
  new ActivityType('Kayaking', 'Kayaking'),
  new ActivityType('RollerSki', 'Rulleski'),
  new ActivityType('Yoga', 'Yoga'),
  new ActivityType('IceSKATE', 'Skøyter'),
  new ActivityType('EBikeRide', 'El-sykkel')
];
