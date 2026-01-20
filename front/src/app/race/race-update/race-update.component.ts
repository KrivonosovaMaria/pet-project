import {Component, OnInit} from '@angular/core';
import {NavigateDirective} from "../../navigate.directive";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {GlobalService} from "../../global.service";
import {RaceService} from "../race.service";
import {TrackService} from "../../track/track.service";

@Component({
	selector: 'app-race-update',
	imports: [
		NavigateDirective,
		ReactiveFormsModule
	],
	templateUrl: './race-update.component.html',
	standalone: true
})

export class RaceUpdateComponent implements OnInit {

	id: number = 0

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
		private activatedRoute: ActivatedRoute,
	) {
	}

	ngOnInit(): void {
		if (this.global.role !== 'ADMIN') this.router.navigate(['/login']);

		this.activatedRoute.queryParams.subscribe(params => {
			this.id = params['id'];
			this.raceService.find(params['id']).subscribe({
				next: (res: any) => {
					this.raceFormGroup.setValue({
						name: res.data.name,
						date: res.data.date,
						description: res.data.description,
						count: res.data.count,
						price: res.data.price,
					})
					this.trackId = res.data.trackId;
				},
				error: (e: any) => {
					console.log(e.error)
					if (e.error.code === 404) {
						this.router.navigate(['/error'], {queryParams: {message: e.error.message}});
					} else {
						this.router.navigate(['/login']);
					}
				}
			})
		})

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

		return true;
	}

	update() {
		this.raceService.update(this.id, this.raceFormGroup.value, this.trackId, this.img);
	}

}
