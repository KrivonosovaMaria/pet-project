import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {RaceService} from "../race.service";
import {NavigateDirective} from "../../navigate.directive";
import {TrackService} from "../../track/track.service";

@Component({
	selector: 'app-race-add',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './race-add.component.html',
	standalone: true
})

export class RaceAddComponent implements OnInit {

	raceFormGroup = new FormGroup({
		name: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		date: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(255)]),
		description: new FormControl("", [Validators.required, Validators.minLength(1), Validators.maxLength(5000)]),
		count: new FormControl(null, [Validators.required, Validators.min(1), Validators.max(1000000)]),
		price: new FormControl(null, [Validators.required, Validators.min(0.01), Validators.max(1000000)]),
	})

	img: any = null;

	tracks: any[] = [];
	trackId: number = 0;

	constructor(
		private router: Router,
		private global: GlobalService,
		private raceService: RaceService,
		private trackService: TrackService,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);

		this.trackService.trackSubject.subscribe(value => {
			this.tracks = value.tracks;
		})
		this.trackService.findAll();
	}

	changeImg(event: any) {
		this.img = event.target.files;
	}

	changeTrackId(event: any) {
		this.trackId = event.target.value;
	}

	checkSubmit(): boolean {
		if (this.raceFormGroup.invalid) return false;
		if (this.trackId === 0) return false;
		if (this.trackId == 0) return false;
		if (this.img === null) return false;

		return true;
	}

	save() {
		this.raceService.save(this.raceFormGroup.value, this.trackId, this.img);
	}

}
