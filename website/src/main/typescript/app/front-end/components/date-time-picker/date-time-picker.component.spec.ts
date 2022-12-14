import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DateTimePicker } from './date-time-picker.component';

describe('DateTimePickerComponent', () => {
  let component: DateTimePicker;
  let fixture: ComponentFixture<DateTimePicker>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DateTimePicker],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DateTimePicker);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
