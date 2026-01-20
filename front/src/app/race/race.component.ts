import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../global.service";
import {RaceService} from "./race.service";
import {NgIf} from "@angular/common";
import {NavigateDirective} from "../navigate.directive";
import {FormsModule} from "@angular/forms";

@Component({
	selector: 'app-race',
	imports: [
		NgIf,
		NavigateDirective,
		FormsModule
	],
	templateUrl: './race.component.html',
	standalone: true
})

export class RaceComponent implements OnInit {

	races: any[] = [];
	filterName: string = '';
	filterTrackName: string = '';

	get racesSorted() {
		let res = this.races;

		if (this.filterName !== '') res = res.filter((i: any) => i.name.toLowerCase().includes(this.filterName.toLowerCase()));
		if (this.filterTrackName !== '') res = res.filter((i: any) => i.trackName.toLowerCase().includes(this.filterTrackName.toLowerCase()));

		return res;
	}

	constructor(
		private global: GlobalService,
		private raceService: RaceService,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.raceService.raceSubject.subscribe(value => {
			this.races = value.races;
		})
		this.raceService.findAll();
	}

}
