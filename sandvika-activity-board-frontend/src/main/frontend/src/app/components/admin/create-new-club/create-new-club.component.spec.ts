import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateNewClubComponent } from './create-new-club.component';

describe('CreateNewClubComponent', () => {
  let component: CreateNewClubComponent;
  let fixture: ComponentFixture<CreateNewClubComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateNewClubComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateNewClubComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
