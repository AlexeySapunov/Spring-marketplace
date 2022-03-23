import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {Page} from "../../model/page";

@Component({
  selector: 'app-product-paginator',
  templateUrl: './product-paginator.component.html',
  styleUrls: ['./product-paginator.component.less']
})
export class ProductPaginatorComponent implements OnInit, OnChanges {

  @Input() page?: Page;

  numbers: number[] = [];

  pageNumber: number = 1;

  @Output() goToPageEvent = new EventEmitter<number>();

  constructor() { }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.page != null) {
      this.numbers = Array.from(Array(this.page.totalPages).keys());
      this.pageNumber = this.page.number + 1;
    }
  }

  goToPage(page: number) {
    this.goToPageEvent.emit(page);
  }

  ngOnInit(): void {
  }
}
