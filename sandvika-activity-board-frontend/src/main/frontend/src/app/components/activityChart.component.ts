import { Component, Input, OnInit, ViewChild, ChangeDetectorRef } from '@angular/core';
import { Activity } from '../domain/activity';
import { empty } from 'rxjs/Observer';
import { ActivityType } from '../domain/ActivityType';
import { ChartsModule, BaseChartDirective } from 'ng2-charts';

@Component({
  selector: 'app-activity-chart',
  templateUrl: 'activityChart.component.html'
})
export class ActivityChartComponent implements OnInit {
  @Input() activities: Activity[];

  @ViewChild('baseChart') chart: BaseChartDirective;

  navOptions = [
    {
      id: 'handicap',
      title: 'Handicap-utvikling'
    },
    {
      id: 'velocity',
      title: 'Gjennomsnittshastighet'
    }
  ];

  selectedNavOption: string;

  // bouvet colors (from styleguide)
  private orange = {
    backgroundColor: '#FFAD78',
    borderColor: '#FC863A',
    pointBackgroundColor: '#FF6400',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#FFAD78'
  };

  private yellow = {
    backgroundColor: '#FFDD8E',
    borderColor: '#FFCC55',
    pointBackgroundColor: '#FBBD2D',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#FFDD8E'
  };

  private blue = {
    backgroundColor: '#A4D4EB',
    borderColor: '#ABE1F9',
    pointBackgroundColor: '#EBF9FF',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#A4D4EB'
  };

  private teal = {
    backgroundColor: '#7DC8C6',
    borderColor: '#5AA4A2',
    pointBackgroundColor: '#41747A',
    pointBorderColor: '#FFF',
    pointHoverBackgroundColor: '#FFF',
    pointHoverBorderColor: '#7DC8C6'
  };

  public labels = [];
  public datasets = [];
  public options = {};
  public colors = [this.orange, this.yellow, this.blue, this.teal];
  public legend = true;
  public type = 'line';

  constructor(private changeDetector: ChangeDetectorRef) {}

  ngOnInit() {
    this.select(this.navOptions[0].id);
  }

  refreshChart() {
    // Workaround to fix faulty ng2-charts issue with updating labels, colors, options etc.
    if (this.chart) {
      this.changeDetector.detectChanges();
      this.chart.ngOnDestroy();
      this.chart.chart = this.chart.getChartBuilder(this.chart.ctx);
    }
  }

  select(selected: string): void {
    this.selectedNavOption = selected;

    switch (selected) {
      case 'handicap':
        this.drawHandicap();
        break;
      case 'velocity':
        this.drawVelocity();
        break;
    }

    this.refreshChart();
  }

  drawHandicap(): void {
    const handicapData = [];

    for (let i = 0; i < this.activities.length; i++) {
      const j = this.activities.length - (1 + i);

      const formattedDate = this.convertDate(this.activities[j].startDateLocal);
      const handicap = this.activities[j].handicap;

      this.labels[i] = formattedDate;
      handicapData[i] = { t: formattedDate, y: handicap };
    }

    this.setOptions('Tid', 'Handicap');
    this.datasets = [{ data: handicapData, label: 'Handicaputvikling' }];
  }

  drawVelocity(): void {
    const velocityData = [];

    for (let i = 0; i < this.activities.length; i++) {
      const j = this.activities.length - (1 + i);
      const activity = this.activities[j];

      const formattedDate = this.convertDate(activity.startDateLocal);
      const velocity = this.calculateVelocity(activity.distanceInMeters, activity.movingTimeInSeconds);

      this.labels[i] = formattedDate;
      velocityData[i] = { t: formattedDate, y: velocity };
    }

    this.setOptions('Tid', 'km / h');
    this.datasets = [{ data: velocityData, label: 'Gjennomsnittshastighet' }];
  }

  setOptions(xAxisLabel: string, yAxisLabel: string) {
    this.options = {
      scales: {
        xAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: xAxisLabel
            }
          }
        ],
        yAxes: [
          {
            scaleLabel: {
              display: true,
              labelString: yAxisLabel
            }
          }
        ]
      }
    };
  }

  convertDate(inputFormat): any {
    function pad(s) {
      return s < 10 ? '0' + s : s;
    }
    const d = new Date(inputFormat);
    return [pad(d.getDate()), pad(d.getMonth() + 1), d.getFullYear()].join('/');
  }

  calculateVelocity(distanceInMetres, timeInSeconds): number {
    const distanceIsValid =
      distanceInMetres && !isNaN(distanceInMetres) && distanceInMetres !== 0;
    const timeIsValid =
      timeInSeconds && !isNaN(timeInSeconds) && timeInSeconds !== 0;

    let velocity = 0;

    if (distanceIsValid && timeIsValid) {
      velocity = distanceInMetres / timeInSeconds;
    }

    return this.convertmetersPerSecToKmPerHour(velocity);
  }
  convertmetersPerSecToKmPerHour(metersPerSecond: number): number {
    const kmPerHour = metersPerSecond * 3.6;

    return +kmPerHour.toPrecision(3);
  }
}
