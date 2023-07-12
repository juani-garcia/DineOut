export class Category {
    static readonly ITALIAN = new Category('ITALIAN', 'Category.italian')
    static readonly AMERICAN = new Category('AMERICAN', 'Category.american')
    static readonly SPANISH = new Category('SPANISH', 'Category.spanish')
    static readonly PIZZA = new Category('PIZZA', 'Category.pizza')
    static readonly STEAKHOUSE = new Category('STEAKHOUSE', 'Category.steakhouse')
    static readonly BAR = new Category('BAR', 'Category.bar')
    static readonly CHINESE = new Category('CHINESE', 'Category.chinese')
    static readonly VIET_THAI = new Category('VIET_THAI', 'Category.viet_thai')
    static readonly BURGERS = new Category('BURGERS', 'Category.burgers')
    static readonly JAPANESE = new Category('JAPANESE', 'Category.japanese')
    static readonly SUSHI = new Category('SUSHI', 'Category.sushi')
    static readonly VEGGIE = new Category('VEGGIE', 'Category.veggie')
    static readonly INDIAN = new Category('INDIAN', 'Category.indian')
    static readonly EMPANADAS = new Category('EMPANADAS', 'Category.empanadas')
    static readonly BOULANGERIE = new Category('BOULANGERIE', 'Category.boulangerie')
    static readonly COFFEE = new Category('COFFEE', 'Category.coffee')
    static readonly BREWERY = new Category('BREWERY', 'Category.brewery')
    static readonly PERUVIAN = new Category('PERUVIAN', 'Category.peruvian')
    static readonly NIKKEI = new Category('NIKKEI', 'Category.nikkei')
    static readonly ARABIAN = new Category('ARABIAN', 'Category.arabian')
    static readonly MEXICAN = new Category('MEXICAN', 'Category.mexican')
    static readonly GELATO = new Category('GELATO', 'Category.gelato')

    private constructor (private readonly name: string, public readonly description: string) {

    }

    toString (): string {
        return this.description
    }
}