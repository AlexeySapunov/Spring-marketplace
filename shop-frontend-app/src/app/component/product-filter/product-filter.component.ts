import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ProductFilter} from "../../model/productFilter";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.less']
})
export class ProductFilterComponent implements OnInit {

  nameFilter?: ProductFilter;

  @Output() filterApplied = new EventEmitter<ProductFilter>();

  constructor() { }

  applyFilter() {
    this.filterApplied.emit(this.nameFilter);
  }

  ngOnInit(): void {
  }

}
