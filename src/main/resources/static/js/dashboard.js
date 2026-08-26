function loadUserName(){
	const querryString = window.location.search;
	const querryParams =  new URLSearchParams(querryString);
	document.getElementById('ue').innerText = querryParams.get('userEmail');
}


function closeTable(){
	document.getElementById("dt").innerText="";
	document.getElementById("num").innerText="";
	document.getElementById("tab").innerText="";
	document.getElementById("stat").innerText="";
	document.getElementById("pmt").innerText="";
	document.getElementById("itmcode").value="";
	document.getElementById("itmlist").value="Select an item...";
	document.getElementById("qty").value="";
	document.getElementById("srno").value="";
		
			
}

function toggleModal() {
    const modal = document.getElementById('registerModal');
    modal.style.display = (modal.style.display === 'flex') ? 'none' : 'flex';
}

function saveItem() {
    const name = document.getElementById('newItemName').value;
    if(name) {
        alert(name + " added!");
        toggleModal();
    }
}