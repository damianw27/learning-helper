import { ChangeDetectionStrategy, Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgbDateStruct, NgbTimeStruct } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-date-time-picker',
  templateUrl: './date-time-picker.component.html',
  styleUrls: ['./date-time-picker.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DateTimePickerComponent implements OnInit {
  @Input() formControl: FormControl = new FormControl('');

  time: NgbTimeStruct = { hour: 0, minute: 0, second: 0 };
  date: NgbDateStruct = { day: 1, month: 1, year: 2022 };

  constructor() {}

  ngOnInit(): void {
    this.formControl.setValue(this.getDateTimeValue());
  }

  public onDateTimeChange() {
    this.formControl.setValue(this.getDateTimeValue());
  }

  private getDateTimeValue(): string {
    const month = this.getNumberAsTwoDigits(this.date.month);
    const day = this.getNumberAsTwoDigits(this.date.day);
    const hour = this.getNumberAsTwoDigits(this.time.hour);
    const minute = this.getNumberAsTwoDigits(this.time.minute);
    const second = this.getNumberAsTwoDigits(this.time.second);
    return `${this.date.year}-${month}-${day} ${hour}:${minute}:${second}`;
  }

  private getNumberAsTwoDigits(value: number) {
    return value.toLocaleString('en-US', { minimumIntegerDigits: 2, useGrouping: false });
  }
}
