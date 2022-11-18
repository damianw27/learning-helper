import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss'],
})
export class PaginationComponent {
  @Input() currentPage: number | null = 1;
  @Input() pagesCount: number | null = 1;

  @Output() pageChange = new EventEmitter<number>();

  constructor() {}

  public setCurrentPage(index: number): void {
    this.currentPage = index;
  }

  public getPages() {
    return [...Array(this.pagesCount).keys()].map(value => value + 1);
  }
}
