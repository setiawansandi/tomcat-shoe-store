function validateForm() {
  var email = document.getElementById("email").value;
  var password = document.getElementById("password").value;
  var errorMessage = document.getElementById("errorMessage");

  console.log("triggered");

  // Email validation regex pattern
  var emailPattern = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

  // Check if email field is empty or invalid
  if (!emailPattern.test(email) || email.trim() === "") {
    errorMessage.textContent = "Please enter a valid email address.";
    return false;
  }

  // Check if password field is empty
  if (password.trim() === "") {
    errorMessage.textContent = "Please enter your password.";
    return false;
  }

  // Clear any previous error message
  errorMessage.textContent = "";

  // Form is valid
  return true;
}

function validateFormReg() {
  var fname = document.getElementById("firstName").value;
  var email = document.getElementById("email").value;
  var password = document.getElementById("password").value;
  var errorMessage = document.getElementById("errorMessage");

  console.log("triggered");

  // Email validation regex pattern
  var emailPattern = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;

  // Check if email field is empty or invalid
  if (!emailPattern.test(email) || email.trim() === "") {
    errorMessage.textContent = "Please enter a valid email address.";
    return false;
  }

  // Check if first name is empty
  if (fname.trim() === "") {
    errorMessage.textContent = "First name can't be empty.";
    return false;
  }

  // Check if password field is empty
  if (password.trim() === "") {
    errorMessage.textContent = "Please enter your password.";
    return false;
  }

  // Clear any previous error message
  errorMessage.textContent = "";

  // Form is valid
  return true;
}
