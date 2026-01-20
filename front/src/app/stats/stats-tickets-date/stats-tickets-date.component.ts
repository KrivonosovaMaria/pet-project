import {Component, OnInit} from '@angular/core';
import {StatsService} from "../stats.service";
import {ChartComponent} from "ng-apexcharts";

@Component({
	selector: 'app-stats-tickets-date',
	imports: [
		ChartComponent
	],
	templateUrl: './stats-tickets-date.component.html',
	standalone: true,
})

export class StatsTicketsDateComponent implements OnInit {

	chartOptions: any;

	names: any[] = [];
	values: any[] = [];

	constructor(
		private service: StatsService,
	) {
	}

	ngOnInit(): void {
		this.service.ticketsDate().subscribe({
			next: (res: any) => {
				this.names = res.data.names;
				this.values = res.data.values;
				console.log(this.names);
				console.log(this.values);
				this.draw();
			},
			error: (e: any) => {
				console.log(e.error)
			}
		})
	}

	draw() {
		this.chartOptions = {
			series: [
				{
					name: "Прибыль",
					data: this.values,
				},
			],
			chart: {
				height: 400,
				type: "line",
			},
			stroke: {
				width: 7,
				curve: "smooth",
			},
			xaxis: {
				type: "date",
				categories: this.names,
			},
			fill: {
				type: "gradient",
				gradient: {
					shade: "dark",
					gradientToColors: ["#FDD835"],
					shadeIntensity: 1,
					type: "horizontal",
					opacityFrom: 1,
					opacityTo: 1,
					stops: [0, 100, 100, 100],
				},
			},
			markers: {
				size: 10,
				colors: ["#FFA41B"],
				strokeColors: "#fff",
				strokeWidth: 2,
				hover: {
					size: 7,
				},
			},
			yaxis: {
				title: {
					text: "Прибыль",
				},
			},
		};
	}

}
