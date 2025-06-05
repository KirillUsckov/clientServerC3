angular.module('propertyApp', [])
    .controller('PropertyController', function($scope, $http, $window) {
        $scope.properties = [];
        $scope.fallbackImg = 'https://via.placeholder.com/100';

        $scope.load = function() {
            $http.get('/api/v1/property').then(function(res) {
                $scope.properties = res.data.properties;
            });
        };

        $scope.delete = function(id) {
            if (confirm("Удалить объект?")) {
                $http.delete('/api/v1/property/' + id).then($scope.load);
            }
        };

        $scope.goToCreate = function() {
            $window.location.href = 'create.html';
        };

        $scope.load();
    })
    .controller('CreateController', function($scope, $http, $window) {
        $scope.form = {};

        $scope.submit = function() {
            const formData = new FormData();
            formData.append('data', new Blob([JSON.stringify($scope.form)], { type: 'application/json' }));

            const files = document.getElementById('photos').files;
            if (files.length > 5) return alert("Не больше 5 фото");

            for (let i = 0; i < files.length; i++) {
                formData.append('photo', files[i]);
            }

            $http.post('/api/v1/property', formData, {
                headers: { 'Content-Type': undefined }
            }).then(() => {
                alert('Успешно!');
                $window.location.href = 'main.html';
            }, () => {
                alert('Ошибка при создании');
            });
        };
    });

