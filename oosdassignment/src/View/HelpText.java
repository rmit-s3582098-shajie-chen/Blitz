package View;

/**
 * Storage of the help information.
 * @author Liam
 */
public class HelpText {
	public static final String text = "Aim of the game:\r\n"
			+ "The team with the merchant needs to escape past the enemy (get to the top of the map).\r\n"
			+ "The other team's only goal is to stop the merchant.\r\n" + "\r\n"
			+ "Piece		Power	Move	Ability\r\n" + "------------------------------------\r\n"
			+ "Merchant	 0	2	Bring accross map to win.\r\n" + "Guard		 1	3	\r\n"
			+ "Pikeman		 3	2	\r\n" + "Assasin		 1	4	\r\n"
			+ "Wizard		 2	2	\r\n"
			+ "Evil Knight	 3	3	\r\n" + "\r\n"
			+ "Units can only 'capture' enemy pieces with an equal or lower 'power' level. "
			+ "Picking up shoes gives +1 extra movement. "
			+ "You can combine pieces into a group to take advantages of their individual qualities. "
			+ "Enabling defensive mode before a units move gives them a shield that halves their movement but adds 1 to their power.";
}
