document.getElementById('createPropertyForm').addEventListener('submit', function (e) {
    e.preventDefault();
    const form = e.target;

    const data = {
        name: form.name.value,
        price: parseFloat(form.price.value),
        type: form.type.value,
        address: form.address.value,
        description: form.description.value,
        period: form.period.value
    };

    const formData = new FormData();
    formData.append('data', new Blob([JSON.stringify(data)], { type: 'application/json' }));

    const files = form.photos.files;
    if (files.length > 1) {
        alert("Поддерживается только 1 фотография.");
        return;
    }

    if (files.length === 1) {
        formData.append('photo', files[0]);
    }

    fetch('/api/v1/property', {
        method: 'POST',
        body: formData
    })
        .then(res => res.json())
        .then(res => {
            if (res.success) {
                alert("SUCCESS")
            } else {
                alert('Ошибка: ' + (res.details || 'Неизвестная ошибка'));
            }
        })
        .catch(() => alert('Произошла ошибка при отправке данных.'));
});
