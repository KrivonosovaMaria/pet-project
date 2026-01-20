import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../global.service";
import {TrackService} from "./track.service";
import {NgIf} from "@angular/common";
import {NavigateDirective} from "../navigate.directive";
import {FormsModule} from "@angular/forms";

@Component({
	selector: 'app-track',
	imports: [
		NgIf,
		NavigateDirective,
		FormsModule
	],
	templateUrl: './track.component.html',
	standalone: true
})

export class TrackComponent implements OnInit {

	tracks: any[] = [];
	filterName: string = '';

	get tracksSorted() {
		let res = this.tracks;

		if (this.filterName !== '') res = res.filter((i: any) => i.name.toLowerCase().includes(this.filterName.toLowerCase()));

		return res;
	}

	constructor(
		private global: GlobalService,
		private trackService: TrackService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.trackService.trackSubject.subscribe(value => {
			this.tracks = value.tracks;
		})
		this.trackService.findAll();
	}

}
