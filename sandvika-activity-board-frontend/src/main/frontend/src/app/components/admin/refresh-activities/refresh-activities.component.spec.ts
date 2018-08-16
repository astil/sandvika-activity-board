import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RefreshActivitiesComponent } from './refresh-activities.component';

describe('RefreshActivitiesComponent', () => {
  let component: RefreshActivitiesComponent;
  let fixture: ComponentFixture<RefreshActivitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RefreshActivitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshActivitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
