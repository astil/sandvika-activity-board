import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {AppRestService} from '../../../service/app.rest.service';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-delete-activity',
  templateUrl: './delete-activity.component.html'
})
export class DeleteActivityComponent implements OnInit {

  @Output()
  notify: EventEmitter<string> = new EventEmitter<string>();

  constructor(private restService: AppRestService) { }

  ngOnInit() {
  }

  deleteActivity(form: NgForm): void {
    this.restService.deleteActivity(form.value.activity).subscribe(() => {
      this.notify.emit(`${form.value.activity} har blitt slettet`);
      form.reset();
    })
  }
}
