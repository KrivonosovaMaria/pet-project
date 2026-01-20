import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {NgApexchartsModule} from "ng-apexcharts";
import {FormsModule} from "@angular/forms";
import {GlobalService} from "../global.service";
import {RaceService} from "../race/race.service";
import * as XLSX from 'xlsx';
import {StatsRaceTicketComponent} from "./stats-race-ticket/stats-race-ticket.component";
import {StatsRaceCommentsComponent} from "./stats-race-comments/stats-race-comments.component";
import {StatsSalesRatioComponent} from "./stats-sales-ratio/stats-sales-ratio.component";
import {TrackService} from "../track/track.service";
import {StatsTrackComponent} from "./stats-track/stats-track.component";

@Component({
	selector: 'app-stats',
	standalone: true,
	imports: [
		NgApexchartsModule,
		FormsModule,
		StatsRaceTicketComponent,
		StatsRaceCommentsComponent,
		StatsSalesRatioComponent,
		StatsTrackComponent
	],
	templateUrl: './stats.component.html',
})

export class StatsComponent implements OnInit {

	races: any[] = [];
	tracks: any[] = [];

	constructor(
		private router: Router,
		private global: GlobalService,
		private raceService: RaceService,
		private trackService: TrackService,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);

		this.raceService.raceSubject.subscribe(value => {
			this.races = value.races;
		})
		this.raceService.findAll();

		this.trackService.trackSubject.subscribe(value => {
			this.tracks = value.tracks;
		})
		this.trackService.findAll();
	}

	exportToExcel(): void {
		const table = document.getElementById('excel');
		const ws: XLSX.WorkSheet = XLSX.utils.table_to_sheet(table);
		const wb: XLSX.WorkBook = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(wb, ws, 'excel');
		XLSX.writeFile(wb, 'excel.xlsx');
	}

}
