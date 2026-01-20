import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../global.service";
import {TrackService} from "../track.service";
import {ActivatedRoute, Router} from "@angular/router";
import {NavigateDirective} from "../../navigate.directive";
import {NgIf} from "@angular/common";

@Component({
	selector: 'app-track-page',
	imports: [
		NavigateDirective,
		NgIf
	],
	templateUrl: './track-page.component.html',
	standalone: true
})

export class TrackPageComponent implements OnInit {

	track: any = {
		name: ''
	};

	constructor(
		private global: GlobalService,
		private trackService: TrackService,
		private activatedRoute: ActivatedRoute,
		private router: Router,
	) {
	}

	get role() {
		return this.global.role;
	}

	ngOnInit(): void {
		this.activatedRoute.queryParams.subscribe(params => {
			this.trackService.find(params['id']).subscribe({
				next: (res: any) => this.track = res.data,
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
	}

	delete() {
		this.trackService.delete(this.track.id);
	}
}
