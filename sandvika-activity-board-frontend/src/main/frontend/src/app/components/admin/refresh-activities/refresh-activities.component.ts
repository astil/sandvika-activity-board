import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {NgForm} from '@angular/forms';
import {AppRestService} from '../../../service/app.rest.service';

@Component({
  selector: 'app-refresh-activities',
  templateUrl: './refresh-activities.component.html'
})
export class RefreshActivitiesComponent implements OnInit {


  isLoading = false;
  @Output()
  success: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  error: EventEmitter<string> = new EventEmitter<string>();


  constructor(private restService: AppRestService) { }

  ngOnInit() {

  }

  refreshActivities(form: NgForm): void {
    this.isLoading = true;
    this.restService.refreshActivities(form.value.pages).subscribe(() => {
      this.success.emit('Oppdatert');
      this.isLoading = false;
      form.reset();
    }, (error) => {
      this.error.emit(error);
      this.isLoading = false;
    });
  }

}
