<#macro Cockt_list cocktail_list string>
    <div class="coctail__info">
        <div class="container">
            <div class="coctail__inner">
                <div class="coctail__header single__header">
                    <span class="coctail__name single__name">${string}</span>
                </div>
                <div class="single__inner-items">
                    <#list cocktail_list as cocktail>
                        <div class="single__item">
                            <a href="/BarBookOriginal_war/cocktail?id=${cocktail.id}" class="single__item-photo">
                                <img src="/BarBookOriginal_war/img?image_path=${cocktail.smallImg}" alt="" class="single__cocktail-img">
                                <div class="single__cocktail-name">${cocktail.name}</div>
                            </a>
                            <div class="single__content-ingredient">
                                <#list cocktail.ingridients as ing>
                                    <a href="/BarBookOriginal_war/ingridient?id=${ing.id}" class="single__ingredient-item">
                                        <img src="/BarBookOriginal_war/img?image_path=${ing.smallImg}" alt="" class="single__ingred-img">
                                        <div class="single__ingred-name">${ing.name}</div>
                                    </a>
                                </#list>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
        <#nested>
    </div>
</#macro>