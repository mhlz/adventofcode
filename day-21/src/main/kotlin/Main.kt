
interface Actor {

    fun calcArmor(): Int

    fun calcDamage(): Int

}

class Player(
        var hitpoints: Int = 100,
        var weapon: Weapon,
        var rings: List<Ring> = arrayListOf(),
        var armor: Armor? = null
) : Actor {
    override fun calcArmor(): Int {
        var ret = rings.map { it.armor }.sum()
        if(armor != null) {
            ret += armor!!.armor
        }
        return ret + weapon.armor
    }

    override fun calcDamage(): Int {
        var ret = rings.map { it.damage }.sum()
        if(armor != null) {
            ret += armor!!.damage
        }
        return ret + weapon.damage
    }

    fun turn(boss: Boss): Actor? {
        val myDamage = calcDamage()
        val bossArmor = boss.calcArmor()

        val diff = Math.max(myDamage - bossArmor, 1)

        boss.hitpoints -= diff
        if(boss.hitpoints <= 0) {
            return this
        }

        val bossDamage = boss.calcDamage()
        val myArmor = calcArmor()

        hitpoints -= Math.max(bossDamage - myArmor, 1)
        if(hitpoints <= 0) {
            return boss
        }

        return null
    }

    fun calcCost(): Int {
        var ret = rings.map { it.cost }.sum()
        ret += weapon.cost
        ret += armor?.cost ?: 0
        return ret
    }
}

class Armor(cost: Int, damage: Int, armor: Int) : Equip(cost, damage, armor) {}

class Ring(cost: Int, damage: Int, armor: Int) : Equip(cost, damage, armor) {}

class Weapon(cost: Int, damage: Int, armor: Int) : Equip(cost, damage, armor) {

}

open class Equip(
        val cost: Int,
        val damage: Int,
        val armor: Int
)

class Boss(
        var hitpoints: Int = 103,
        val damage: Int = 9,
        val armor: Int = 2
) : Actor {
    override fun calcArmor(): Int {
        return armor
    }

    override fun calcDamage(): Int {
        return damage
    }
}

val weapons = arrayListOf(
        Weapon(8, 4, 0),
        Weapon(10, 5, 0),
        Weapon(25, 6, 0),
        Weapon(40, 7, 0),
        Weapon(74, 8, 0)
)

val armors = arrayListOf(
        Armor(13, 0, 1),
        Armor(31, 0, 2),
        Armor(53, 0, 3),
        Armor(75, 0, 4),
        Armor(102, 0, 5)
)

val rings = arrayListOf(
        Ring(25, 1, 0),
        Ring(50, 2, 0),
        Ring(100, 3, 0),
        Ring(20, 0, 1),
        Ring(40, 0, 2),
        Ring(80, 0, 3)
)

fun tryEquip() {
    weapons.forEach {
        val player = Player(weapon = it)

        tryArmor(player)
    }
}

fun tryArmor(player: Player) {
    player.armor = null
    tryRings(player)

    armors.forEach {
        player.armor = it
        tryRings(player)
    }
}

fun tryRings(player: Player) {
    // no rings
    attack(player)

    // 1 ring
    rings.forEach {
        player.rings = arrayListOf(it)
        attack(player)
    }

    // 2 rings
    rings.forEach { left ->
        rings.forEach { right ->
            if(left != right) {
                player.rings = arrayListOf(left, right)
                attack(player)
            }
        }
    }
}

var boss = Boss()
var min = Integer.MAX_VALUE
var max = Integer.MIN_VALUE

fun attack(player: Player) {
    var winner: Actor?
    while(true) {
        winner = player.turn(boss)
        if(winner != null) {
            break
        }
    }
    if(winner == player) {
        if(min > player.calcCost()) {
            min = player.calcCost()
        }
    } else {
        if(max < player.calcCost()) {
            max = player.calcCost()
        }
    }

    boss = Boss()
    player.hitpoints = 100
}

/**
 * @author Mischa Holz
 */
fun main(args: Array<String>) {

    tryEquip()

    println(min)
    println(max)

}
