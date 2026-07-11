const ul2 = document.getElementById("ul2");

document.getElementById("ipInputForm").addEventListener("submit", function(e) {
    e.preventDefault();
    
    const ip = document.getElementById("ipInput").value.trim();
    ul2.innerHTML = "<li style='color:yellow'>scanning..</li>";

    fetch(`http://localhost:8080/api/siem/portScan?data=${ip}`, {
        method: "GET"
    })
    .then(res => res.json())
    .then(data => {
        ul2.innerHTML = "";

        data.forEach(item => {
            let li = document.createElement("li");
            
            let bannerText = item.banner ? ` - Banner: ${item.banner}` : "";
            li.textContent = `Port: ${item.port} - State: ${item.state}${bannerText}`;
            li.style.color = item.state === "OPEN" ? "lime" : "gray";
            ul2.appendChild(li);
        });
    })
    .catch(err => {
        ul2.innerHTML = `<li style='color:orange'>Error: ${err.message}</li>`;
        console.error(err);
    });
});
  

document.getElementById("uploadForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const fileInput = document.getElementById("fileInput");
    const file = fileInput.files[0];

    if (!file) {
        alert("Select your file first!");
        return;
    }

    const formData = new FormData();
    formData.append("file", file);

    fetch("http://localhost:8080/api/siem/upload", {
        method: "POST",
        body: formData
    })
    .then(res => res.json())
    .then(data => {
    
        ul1.innerHTML = "";

        data.forEach(alert => {
            let li = document.createElement('li');
            li.textContent = `${alert.type} - ${alert.message} (IP: ${alert.ip})`;
            ul1.appendChild(li);
        });

    })
    .catch(err => console.error(err));
});