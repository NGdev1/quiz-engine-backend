<div class="title">Выберите способ создания вопроса:</div>

<button onclick="createQuestionAutomatically()" class="button-primary">Автоматически, по предмету</button>
<button onclick="createQuestionByType()" class="button-primary">По типу сущности</button>
<button onclick="createQuestionManually()" class="button-primary">Вручную</button>

<script type="text/javascript">

    function createQuestionAutomatically() {
        loadContent('/admin/quiz/${quiz.id}/add-question-by-subject', 'Автоматически');
    }
    
    function createQuestionByType() {

    }
    
    function createQuestionManually() {
        loadContent('/admin/quiz/${quiz.id}/add-question-manually', 'Вручную');
    }

</script>