function notifySuccess(msg) {
	$.notify({
		// options		
		icon: 'fa fa-check-circle',
		title: '<strong>Success!</strong>',
		message: msg,
		target: '_blank',
		newest_on_top: true
	},
	{
		// settings
		type: 'success',
		placement: {
			from: 'bottom',
			align: 'right'
		},
		delay: 5000,
		z_index: 4000,
		timer: 500,
		animate: {
			enter: 'animated fadeInDown',
			exit: 'animated fadeOutUp'
		}
	});
}

function notifyWarning(msg) {
	$.notify({
		// options		
		icon: 'fa fa-exclamation-circle',
		title: '<strong>Warning:</strong>',
		message: msg,
		target: '_blank',
		newest_on_top: true
	},
	{
		// settings
		type: 'warning',
		placement: {
			from: 'bottom',
			align: 'right'
		},
		delay: 5000,
		z_index: 4000,
		timer: 1000,
		animate: {
			enter: 'animated fadeInDown',
			exit: 'animated fadeOutUp'
		}
	});
}

function notifyInfo(msg) {
	$.notify({
		// options		
		icon: 'fa fa-info-circle',
		title: '<strong>Info:</strong>',
		message: msg,
		target: '_blank',
		newest_on_top: true
	},
	{
		// settings
		type: 'info',
		placement: {
			from: 'bottom',
			align: 'right'
		},
		delay: 5000,
		z_index: 4000,
		timer: 1000,
		animate: {
			enter: 'animated fadeInDown',
			exit: 'animated fadeOutUp'
		}
	});
}

function notifyError(msg) {
	$.notify({
		// options		
		icon: 'fa fa-times-circle',
		title: '<strong>Error!</strong>',
		message: msg,
		target: '_blank',
		newest_on_top: true
	},
	{
		// settings
		type: 'danger',
		placement: {
			from: 'bottom',
			align: 'right'
		},
		delay: 5000,
		z_index: 4000,
		timer: 1000,
		animate: {
			enter: 'animated fadeInDown',
			exit: 'animated fadeOutUp'
		}
	});
}