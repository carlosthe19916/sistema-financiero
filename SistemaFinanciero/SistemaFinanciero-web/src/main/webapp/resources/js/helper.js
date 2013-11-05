var formSubmitted = false;
function onSubmitButton() {
	if (!formSubmitted) {
		formSubmitted = true;
	} else {
		// disable event propagation if form is already submitted
		event.preventDefault();
	}
}