document.addEventListener("DOMContentLoaded", init);

function init() {
  $('#form').submit(()=>{
    const phone = document.getElementById('phone').value;

    if(phone && !_validatePhone(phone)) {
      $('.toast').toast('show');
      return false;
    }
  });

  document.getElementById('anonymous').addEventListener('change', ()=>{
    const feedback = document.getElementById('feedback');
    feedback.classList.toggle('d-none');
    feedback.querySelectorAll('input').forEach(input=>{
      input.value = '';
    });
    document.body.classList.toggle('anonymous');
  });
}

function _validatePhone(phone) {
  let phoneReg = /^(\+?38[0]?[ -]?)?(0\d{9}|[(]?\d{2,3}[)]?[ -]?\d{3}[ -]?\d{2}[ -]?\d{2})$/;
  return phoneReg.test( phone );
}
